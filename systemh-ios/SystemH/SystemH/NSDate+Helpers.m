//
//  NSDate+Helpers.m
//  SystemH
//
//  Created by leong on 5/13/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NSDate+Helpers.h"


@implementation NSDate (NSDate_Helpers)

+ (NSString *) convertTimeStampToDate: (NSString *) timestampAsString {
    
    timestampAsString = [timestampAsString substringToIndex:([timestampAsString length]-3)];    
    NSDate *timestampAsDate = [NSDate dateWithTimeIntervalSince1970:[timestampAsString integerValue]];
    
    NSDateFormatter *format = [[NSDateFormatter alloc] init];
    [format setDateFormat:@"MMM-dd-YYYY HH:mm"];
    
    return [format stringFromDate:timestampAsDate];
}

@end

