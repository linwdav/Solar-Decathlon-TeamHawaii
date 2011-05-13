//
//  aquaponics.m
//  SystemH
//
//  Created by leong on 5/12/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "aquaponicsController.h"
#import "xmlDataParser.h"
#import "NSString+Helpers.h"

#define CIRCULATION @"CIRCULATION"
#define ELECTRICAL_CONDUCTIVITY @"ELECTRICAL_CONDUCTIVITY"
#define DEAD_FISH @"DEAD_FISH"
#define TEMPERATURE @"TEMPERATURE"
#define TURBIDITY @"TURBIDITY"
#define WATER_LEVEL @"WATER_LEVEL"
#define PH @"PH"
#define OXYGEN @"OXYGEN"

#define HOST @"http://localhost:8111"
#define SYSTEM_NAME @"AQUAPONICS"

#define STATE_DATA_URL @"http://localhost:8111/AQUAPONICS/state"

@implementation aquaponicsController

// XML Parser instance
@synthesize parser = _parser;
@synthesize aquaponicsTableView = _aquaponicsTableView;

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
    [_aquaponicsTableView release];
    [_parser release];
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
    self.navigationItem.title = @"Aquaponics";
    
    // Create XML parser instance
    if (!self.parser) {
        xmlDataParser *parserTemp = [[xmlDataParser alloc] init];
        self.parser = parserTemp;
        [parserTemp release];
    }
    
    // Initialize state data 
    NSMutableDictionary *stateDataTemp = [[NSMutableDictionary alloc] init];
    [self.parser setStateData:stateDataTemp];
    [stateDataTemp release];
    
    // Load default values for state data
    [[self.parser stateData] setObject:@"---" forKey:CIRCULATION];
    [[self.parser stateData] setObject:@"---" forKey:ELECTRICAL_CONDUCTIVITY];
    [[self.parser stateData] setObject:@"---" forKey:DEAD_FISH];
    [[self.parser stateData] setObject:@"---" forKey:TEMPERATURE];
    [[self.parser stateData] setObject:@"---" forKey:TURBIDITY];
    [[self.parser stateData] setObject:@"---" forKey:WATER_LEVEL];
    [[self.parser stateData] setObject:@"---" forKey:PH];
    [[self.parser stateData] setObject:@"---" forKey:OXYGEN];
    

    [self.parser sendXMLCommand:HOST fromSystem:SYSTEM_NAME withCommand:@"SET_TEMPERATURE" andArg:@"20"];
    
    // Start timer for countdown - update at 1.0 second intervals
    [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(updateTable)
                                   userInfo:nil repeats:YES];
    [super viewDidLoad];

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    self.aquaponicsTableView = nil;
    self.parser = nil;
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
    if (!self.aquaponicsTableView) {
        self.aquaponicsTableView = tableView;
    }
    
    // Return the number of sections. (number of key-value pairs in dictionary)
    return [[self.parser stateData] count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return 2;
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
                cell.textLabel.text = @"Circulation";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:CIRCULATION] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }
            
            break;
            
        case 1:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Electrical Conductivity";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:ELECTRICAL_CONDUCTIVITY] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        case 2:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Dead Fish";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:DEAD_FISH] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        case 3:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Temperature";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:TEMPERATURE] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        case 4:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Turbidity";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:TURBIDITY] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        case 5:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Water Level";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:WATER_LEVEL] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        case 6:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"pH";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:PH] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        case 7:
            if ([indexPath row] == 0) {
                cell.textLabel.text = @"Oxygen";
                cell.detailTextLabel.text = [(NSString *)[[self.parser stateData] objectForKey:OXYGEN] truncateDecimals:2];
            }
            else {
                cell.textLabel.text = @"";
                cell.detailTextLabel.text = @"---";
            }

            break;
        default:
            break;
    }
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
    [self.parser parseXMLFile:STATE_DATA_URL];   
    
    // Reload Table view
    [self.aquaponicsTableView reloadData];
} // End updateTable


@end
