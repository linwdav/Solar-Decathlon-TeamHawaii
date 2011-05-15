//
//  aquaponicsController.h
//  SystemH
//
//  Created by leong on 5/12/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class xmlDataParser;

@interface aquaponicsController : UIViewController <UITableViewDelegate, UITableViewDataSource> {
    
    xmlDataParser *parser;
    UITableView *aquaponicsTableView;

}

@property (nonatomic, retain) xmlDataParser *parser;
@property (nonatomic, retain) IBOutlet UITableView *aquaponicsTableView;


@end
