//
//  NSString+Helpers.m
//  SystemH
//
//  Created by leong on 5/12/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "NSString+Helpers.h"


@implementation NSString (NSString_Helpers)

// Truncates (does not round) a given string to the given number of decimal places
// decimalPlaces The number of decimal places to keep.
- (NSString *) truncateDecimals: (NSInteger) decimalPlaces {
    
    // Search for decimal
    NSRange indexOfDecimal = [self rangeOfString:@"."];
    
    // Add 1 (for decimal point) and number of decimal places to keep to indexOfDecimal
    NSUInteger newEndOfString = indexOfDecimal.location + 1 + decimalPlaces;
    
    // Get length of original string
    NSUInteger lengthOfString = [self length];
    
    // If no decimal, indexOfDecimal will be 0
    // Also, we don't want to go past the last index of the string
    if (indexOfDecimal.location > 0 && lengthOfString > newEndOfString) {
        // Truncate
        return [self substringToIndex:(newEndOfString)];
    }
    else {
        // or just return itself
        return self;
    }
} // end truncate decimals

@end
