//
//  NSDate+Helpers.h
//  SystemH
//
//  Created by leong on 5/13/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSDate (NSDate_Helpers)

// Formats a string (Epoch long in milliseconds) to a date format MM/dd/YY HH:mm:ss
+ (NSString *) convertTimeStampToDate: (NSString *) timestampAsString;

@end

