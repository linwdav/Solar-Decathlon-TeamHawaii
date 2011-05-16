//
//  HvacController.m
//  SystemH
//
//  Created by leong on 5/13/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "HvacController.h"
#import "xmlDataParser.h"
#import "NSString+Helpers.h"
#import "NSDate+Helpers.h"

#define TEMPERATURE @"TEMPERATURE"

// Host and system information
#define HOST @"http://localhost:8111"
#define SYSTEM_NAME @"HVAC"

#define STATE_DATA_URL @"http://localhost:8111/HVAC/state"

// Temperature Max and Min Range
#define TEMP_MIN 0
#define TEMP_MAX 100

@implementation HvacController
@synthesize currentTempLabel;
@synthesize desiredTempLabel;
@synthesize desiredTempSlider;
@synthesize savedDesiredTempValue;
@synthesize timestampLabel;
@synthesize parser;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)dealloc
{
    [parser release];
    [savedDesiredTempValue release];
    [currentTempLabel release];
    [desiredTempLabel release];
    [desiredTempSlider release];
    [timestampLabel release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    // set title of the navigation bar
    self.navigationItem.title = @"HVAC";
    
    // Create XML parser instance
    if (!self.parser) {
        xmlDataParser *parserTemp = [[xmlDataParser alloc] init];
        self.parser = parserTemp;
        [parserTemp release];
    }
    
    // Set Label and slider to the previously saved slider value
    if (!self.savedDesiredTempValue) {
        // If no slider value present, then put slider in middle of range
        double initialDesiredTemp =  (double)((TEMP_MAX - TEMP_MIN)/2);
        [self.desiredTempSlider setValue: initialDesiredTemp];
        
        self.desiredTempLabel.text = [[NSString stringWithFormat:@"%f", initialDesiredTemp] truncateDecimals:-1];
    }
    
    // else set the slider and label text to where it was previously.
    else {
        [self.desiredTempSlider setValue: [self.savedDesiredTempValue doubleValue]];
        self.desiredTempLabel.text = self.savedDesiredTempValue;
    }

    // Start timer for countdown - update at 1.0 second intervals
    [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateTable)
                                   userInfo:nil repeats:YES];
    
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [self setParser:nil];
    [self setSavedDesiredTempValue:nil];
    [self setCurrentTempLabel:nil];
    [self setDesiredTempLabel:nil];
    [self setDesiredTempSlider:nil];
    [self setTimestampLabel:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Update Methods

// Updates the desired temperature
- (IBAction) setDesiredTempViaSlider {

    self.savedDesiredTempValue = [[NSString stringWithFormat:@"%f", [self.desiredTempSlider value]] truncateDecimals:-1];
    self.desiredTempLabel.text = self.savedDesiredTempValue;
    [self.parser sendXMLCommand:HOST fromSystem:SYSTEM_NAME withCommand:@"SET_TEMPERATURE" andArg:self.savedDesiredTempValue];

    
} // End setDesiredTempViaSlider

// Updates the table in the current view from the XML document at the specified URL.
- (void) updateTable {
    // Update Data
    [self.parser parseXMLFile:STATE_DATA_URL];   
    
    // Set Current Temperature Label
    self.currentTempLabel.text = [(NSString *)[[self.parser stateData] objectForKey:TEMPERATURE] truncateDecimals:2];
    
    // Get latest timestamp
    NSString *timestampAsString = (NSString *)[[self.parser stateData] objectForKey:@"timestamp"];
    
    if (!timestampAsString) {
        self.timestampLabel.text = @"---";
    }
    else {
        self.timestampLabel.text = [NSDate convertTimeStampToDate:timestampAsString];
    }
    
    [self.timestampLabel setNeedsDisplay];
    
    // Reload Table view
    [self.currentTempLabel setNeedsDisplay];
} // End updateTable


@end
