//
//  SystemHAppDelegate.h
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SystemHAppDelegate : NSObject <UIApplicationDelegate> {
    UITabBarController *tabBarController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet UITabBarController *tabBarController;

@end
