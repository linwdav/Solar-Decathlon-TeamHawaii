//
//  EnergyController.h
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import <UIKit/UIKit.h>

@class xmlDataParser;

@interface EnergyController : UIViewController <UITableViewDelegate, UITableViewDataSource> {
    
    // For parsing XML Data
    xmlDataParser *electricParser;
    xmlDataParser *photovoltaicParser;
    
    UITableView *energyTableView;
}
@property (nonatomic, retain) IBOutlet UITableView *energyTableView;

@property (nonatomic, retain) xmlDataParser *electricParser;
@property (nonatomic, retain) xmlDataParser *photovoltaicParser;

@end