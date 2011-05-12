//
//  testAquaponics.m
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import "testAquaponics.h"
#import "xmlDataParser.h"
#import "NSString+Helpers.h"


@implementation testAquaponics
@synthesize aquaponicsText;
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
    [aquaponicsText release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle
- (void) updateText {
    NSString * resultString = [NSString stringWithFormat:@""];
    
    if (!self.parser) {
        xmlDataParser *parserTemp = [[xmlDataParser alloc] init];
        self.parser = parserTemp;
        [parserTemp release];
    }   
    
        [self.parser parseXMLFile:@"http://localhost:8111/AQUAPONICS/state"];
        
        NSString *valueString;
        NSString *stringToAppend;
        
        for (NSString *key in [self.parser stateData]) {
            valueString = (NSString *) [[self.parser stateData] valueForKey:key];
            valueString = [valueString truncateDecimals:2];
            stringToAppend = [NSString stringWithFormat:@"%@: %@\n", key, valueString];
            resultString = [resultString stringByAppendingString:stringToAppend];
        }    
    
    [aquaponicsText setText:resultString];
    [aquaponicsText setNeedsDisplay];
}


- (void)viewDidLoad
{
    
    // Start timer for countdown - update at 1.0 second intervals
    [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateText)
                                   userInfo:nil repeats:YES];
    [super viewDidLoad];
    
    
    // Do any additional setup after loading the view from its nib.
}



- (void)viewDidUnload
{
    [self setAquaponicsText:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
