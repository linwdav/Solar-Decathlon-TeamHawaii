
//
//  EnergyController.m
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import "EnergyController.h"
#import "xmlDataParser.h"
#import "NSString+Helpers.h"

#define KWH @"kWh"
#define ELECTRIC @"POWER"
#define PV @"ENERGY"

#define ELECTRIC_STATE_DATA_URL @"http://localhost:8111/ELECTRIC/state"
#define PV_STATE_DATA_URL @"http://localhost:8111/PHOTOVOLTAIC/state"

#define NUM_SECTIONS 2

@implementation EnergyController

@synthesize energyTableView = _energyTableView;
@synthesize electricParser = _electricParser;
@synthesize photovoltaicParser = _photovoltaicParser;
@synthesize energyBalance = _energyBalance;
@synthesize pvReading = _pvReading;
@synthesize electricReading = _electricReading;

/*
 - (id)initWithStyle:(UITableViewStyle)style
 {
 self = [super initWithStyle:style];
 if (self) {
 // Custom initialization
 }
 return self;
 }
 */

- (void)dealloc
{
    [_pvReading release];
    [_electricReading release];
    [_energyBalance release];
    [_electricParser release];
    [_photovoltaicParser release];
    [_energyTableView release];
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
    self.navigationItem.title = @"Energy";
    
    // Create XML parser instance
    if (!self.electricParser) {
        xmlDataParser *electricParserTemp = [[xmlDataParser alloc] init];
        self.electricParser = electricParserTemp;
        [electricParserTemp release];
    }
    
    // Create XML parser instance
    if (!self.photovoltaicParser) {
        xmlDataParser *photovoltaicParserTemp = [[xmlDataParser alloc] init];
        self.photovoltaicParser = photovoltaicParserTemp;
        [photovoltaicParserTemp release];
    }
    
    // Initialize state data 
    NSMutableDictionary *electricStateDataTemp = [[NSMutableDictionary alloc] init];
    [self.electricParser setStateData:electricStateDataTemp];
    [electricStateDataTemp release];
    
    // Initialize state data 
    NSMutableDictionary *photovoltaicStateDataTemp = [[NSMutableDictionary alloc] init];
    [self.photovoltaicParser setStateData:photovoltaicStateDataTemp];
    [photovoltaicStateDataTemp release];
    
    // Load default values for state data
    [[self.electricParser stateData] setObject:@"---" forKey:ELECTRIC];
    [[self.photovoltaicParser stateData] setObject:@"---" forKey:PV];
    
    // Start timer for countdown - update at 1.0 second intervals
    [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateTable) userInfo:nil repeats:YES];
    
    [super viewDidLoad];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [self setPvReading:nil];
    [self setElectricReading:nil];
    [self setEnergyBalance:nil];
    [self setElectricParser:nil];
    [self setPhotovoltaicParser:nil];
    [self setEnergyTableView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // This method runs first, so use it to assign the table view from the delegate
    // to the local tableview variable
    if (!self.energyTableView) {
        self.energyTableView = tableView;
    }
    return NUM_SECTIONS;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    switch (section) {
            // First section contains two rows (PV and ELECTRIC readings)
        case 0:
            return 2;
            // Second section contains one row (energy delta)
        case 1:
            return 1;
        default:
            return 0;
    } // End Switch
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:CellIdentifier] autorelease];
    }   
    
    // Configure each cell...
    
    switch ([indexPath section]) {
            
        case 0:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Energy Generated";
                
                // Store in instance variable for calculation
                self.pvReading = [(NSString *)[[self.photovoltaicParser stateData] objectForKey:PV] truncateDecimals:2];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%@ kWh",self.pvReading];
            }
            else {
                // Store in instance variable for calculation
                cell.textLabel.text = @"Energy Consumed";
                self.electricReading = [(NSString *)[[self.electricParser stateData] objectForKey:ELECTRIC] truncateDecimals:2];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%@ kWh",self.electricReading];
            }
            
            break;
            
        case 1:
            cell.textLabel.text = @"Energy Balance";
            
            self.energyBalance = [self reportDifferenceBetweenAsDouble:self.pvReading andAnotherValue:self.electricReading];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%@ kWh",self.energyBalance];
            break;
            
        default:
            break;
    } // End Switch
    
    return cell;
}

/*
 // Override to support conditional editing of the table view.
 - (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the specified item to be editable.
 return YES;
 }
 */

/*
 // Override to support editing the table view.
 - (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
 {
 if (editingStyle == UITableViewCellEditingStyleDelete) {
 // Delete the row from the data source
 [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
 }   
 else if (editingStyle == UITableViewCellEditingStyleInsert) {
 // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
 }   
 }
 */

/*
 // Override to support rearranging the table view.
 - (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
 {
 }
 */

/*
 // Override to support conditional rearranging of the table view.
 - (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
 {
 // Return NO if you do not want the item to be re-orderable.
 return YES;
 }
 */

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     [detailViewController release];
     */
}

#pragma mark - Update Table

// Updates the table in the current view from the XML document at the specified URL.
- (void) updateTable {
    // Update Data
    [self.electricParser parseXMLFile:ELECTRIC_STATE_DATA_URL];   
    [self.photovoltaicParser parseXMLFile:PV_STATE_DATA_URL]; 
    
    // Reload Table view
    [self.energyTableView reloadData];
} // End updateTable


#pragma mark - Get Difference Between Two Numbers

// Gets the difference given two string representations (of doubles)
// Reports back the difference = valueOne - valueTwo
- (NSString *) reportDifferenceBetweenAsDouble: (NSString *) valueOne andAnotherValue: (NSString *) valueTwo {
    double valueOneAsDouble = [valueOne doubleValue];
    double valueTwoAsDouble = [valueTwo doubleValue];
    
    return [[NSString stringWithFormat: @"%f", (valueOneAsDouble - valueTwoAsDouble)] truncateDecimals:-1];
} // End reportDifferenceMethod

@end
