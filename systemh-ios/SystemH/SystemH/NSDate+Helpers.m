//
//  NSDate+Helpers.m
//  SystemH
//
//  Created by leong on 5/13/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NSDate+Helpers.h"


@implementation NSDate (NSDate_Helpers)

// Formats a string (Epoch long in milliseconds) to a date format MM/dd/YY HH:mm:ss
+ (NSString *) convertTimeStampToDate: (NSString *) timestampAsString {
    
    // Java timestamp is in milliseconds.  NSDate only takes seconds.  So remove last three digits
    timestampAsString = [timestampAsString substringToIndex:([timestampAsString length]-3)];    
    
    // Convert to an NSDate object
    NSDate *timestampAsDate = [NSDate dateWithTimeIntervalSince1970:[timestampAsString integerValue]];
    
    // Choose date format
    NSDateFormatter *format = [[NSDateFormatter alloc] init];
    [format setDateFormat:@"MM/dd/YY HH:mm:ss"];
    
    return [format stringFromDate:timestampAsDate];
}

@end

