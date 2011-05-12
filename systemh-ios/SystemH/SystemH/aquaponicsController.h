//
//  aquaponicsController.h
//  SystemH
//
//  Created by leong on 5/12/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class xmlDataParser;

@interface aquaponicsController : UITableViewController {
    xmlDataParser *parser;

}

@property (nonatomic, retain) xmlDataParser *parser;



@end
