//
//  xmlDataParser.h
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface xmlDataParser : NSObject <NSXMLParserDelegate> {
    id _delegate;
    
    // Holds state data information from the XML document
    NSMutableDictionary *stateData;
    
    
}

@property (nonatomic, retain) NSMutableDictionary *stateData;

- (BOOL) parseXMLFile:(NSString *) urlPath;


@end
