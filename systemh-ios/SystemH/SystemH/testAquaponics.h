//
//  testAquaponics.h
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import <UIKit/UIKit.h>

@class xmlDataParser;

@interface testAquaponics : UIViewController {
    xmlDataParser *parser;
    UITextView *aquaponicsText;
}
@property (nonatomic, retain) IBOutlet UITextView *aquaponicsText;

@property (nonatomic, retain) xmlDataParser *parser;

@end
