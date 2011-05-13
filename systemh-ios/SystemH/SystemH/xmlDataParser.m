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
    /*
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error" message:@"There was an error that occurred during synching" delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
    [alert show];
    */
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


// Sends the command via PUT request.  All parameters are included in the URL pathname
- (BOOL) sendXMLCommand: (NSString *) hostName fromSystem: (NSString *) systemName withCommand: (NSString *) commandName andArg: (NSString *) argValue {
 
    // Construct full URL for PUT request
    NSString *urlAsString = [[NSString alloc] initWithFormat:@"%@/%@/command/%@?arg=%@", hostName, systemName, commandName, argValue];
        
    // Initialize URL request with URL
    NSURL *fullURL = [NSURL URLWithString:urlAsString];
    NSMutableURLRequest *putRequest = [[NSMutableURLRequest alloc] initWithURL:fullURL];
    
    // Configure request
    [putRequest setTimeoutInterval:5.0];
    [putRequest setHTTPMethod:@"PUT"];
    [putRequest setValue:@"text/xml; charset=UTF-8" forHTTPHeaderField:@"Content-type"];
    
    // Send request
    NSURLConnection *putConnection;
    putConnection = [[NSURLConnection alloc] initWithRequest:putRequest delegate:self];
    
    // Return whether operation succeeded or not
    if (!putConnection) {
        return NO;
    }
    else {
        // Means request went through but it's not certain if successful or not (may recieve 404 status code)
        return YES;
    }
} // End SendXMLCommand

-(void) connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
    NSLog(@"PUT Request Status Code: %i", [(NSHTTPURLResponse *)response statusCode]);
}

// Deallocation Method
- (void) dealloc {
    
    [_stateData release];
    [super dealloc];
}

@end
