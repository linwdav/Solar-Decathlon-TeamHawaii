//
//  xmlDataParser.m
//  SystemH
//
//  Created by Kevin Leong on 5/11/11.
//  Copyright 2011. All rights reserved.
//

#import "xmlDataParser.h"


@implementation xmlDataParser

@synthesize stateData = _stateData;


// Handle XML Parsing Errors
- (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError {

    // Error handling here
}

// Retrieves the state data from the XML document and stores it as key-value pairs
// in an NSMutableDictionary
- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    
    // All data is within "state" tags
    if ( [elementName isEqualToString:@"state"]) {
        
        // Create state data dictionary if it doesn't exist
        if (!self.stateData) {
            NSMutableDictionary *stateDataTemp = [[NSMutableDictionary alloc] init];
            self.stateData = stateDataTemp;
            
            [stateDataTemp release];
        }
        
        // Gets key and value from the attribues dictionary
        NSString * key = (NSString *)[attributeDict objectForKey:@"key"];
        NSString * value = (NSString *)[attributeDict objectForKey:@"value"];
        
        // Stores values in the state data Dictionary
        [self.stateData setObject:value forKey:key];
        
        return;
    } // End if

} // End get attributes method

// Parse the XML file at the given URL
- (BOOL) parseXMLFile:(NSString *) urlPath {
    
    // Convert string to NSURL object
    NSURL *xmlURL = [NSURL URLWithString:urlPath];
    
    // Initialize parser
    NSXMLParser *dataParser = [[NSXMLParser alloc] initWithContentsOfURL:xmlURL];
    
    [dataParser setDelegate:self];
    [dataParser setShouldResolveExternalEntities:YES];
    
    // Boolean value to determine if parsing was successful or not
    BOOL success = [dataParser parse];
    
    [dataParser release];
    return success;
} // end parse XML File


// Deallocation Method
- (void) dealloc {
    
    [_stateData release];
    [super dealloc];
}

@end
