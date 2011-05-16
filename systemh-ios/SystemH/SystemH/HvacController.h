//
//  HvacController.h
//  SystemH
//
//  Created by leong on 5/13/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class xmlDataParser;

@interface HvacController : UIViewController {
    
    UILabel *currentTempLabel;
    UILabel *desiredTempLabel;
    UISlider *desiredTempSlider;
    
    // Saved Desired Temp Value
    NSString *savedDesiredTempValue; 
    
    // Parser to aid in XML parsing and Commands via HTTP PUT requests
    xmlDataParser *parser;

    UILabel *timestampLabel;
}
@property (nonatomic, retain) IBOutlet UILabel *timestampLabel;
@property (nonatomic, retain) xmlDataParser *parser;

@property (nonatomic, retain) NSString *savedDesiredTempValue;

@property (nonatomic, retain) IBOutlet UILabel *currentTempLabel;
@property (nonatomic, retain) IBOutlet UILabel *desiredTempLabel;
@property (nonatomic, retain) IBOutlet UISlider *desiredTempSlider;

- (IBAction) setDesiredTempViaSlider;

@end
