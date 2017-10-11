# Steps Available

This is offline documentation. The original source can be found at: https://github.com/ctco/cukes/wiki.

## Assertions

#### Asserting response properties
    .....
    Then status code is 200
    And response contains property "id" with value other than "2000"
    And response contains property "name" with value "Nexus 9"
    And response contains property "owner.name" with value "Ned"
    And response contains property "owner.surname" with value "Flanders"
    And response contains property "owner.age" with value "43"
    And response contains property "createdDate" of type "long"
    And response does not contain property "updatedDate"

#### Asserting response arrays
    .....
    Then status code is 200
    And response contains an array "devices" of size "5"

#### Finding property with condition
    .....
    Then response contains property "devices.find{devices->device.id=="12345"}.type" with value "PHONE"

#### Asserting Response body from file
    When the client performs GET request on /gadgets/12345
    Then response contains properties from file gadgets/responses/createdGadget.json

In the mentioned example, Gadget with ID 12345 is returned in the response and all properties **from createdGadget.json** are searched in response body, values are asserted to be equal.


---------------

## Async requests

#### Waiting for async Gadget update in the background to be completed

    Given request body from file request.json
    When the client performs PUT request on api/gadgets/12345
    Then status code is 200

    And should wait at most 60 seconds with interval 1 seconds until property "status" equal to "SUCCEEDED"
    When the client performs GET request on {(header.Location)}
    And response contains property "status" with value "SUCCEEDED"

#### Waiting for async Gadget update in the background to be completed or failed

    .....
    And should wait at most 60 seconds with interval 1 seconds until property "status" equal to "SUCCEEDED" or fail with "FAILED"
    When the client performs GET request on {(header.Location)}
    And response contains property "status" with value "SUCCEEDED"



---------------

## Enrich request

#### Adding multiple items to the HTTP request

    Given cookie "LtpaToken2" with value "abcde"
    And baseUri is "http://my.server.com/rest"
    And queryParam "limit" "20"
    And request body "{ "property": "test" }"
    
    or
    And request body: 
    """
    {
       "property": "test"
    }
    """

    or
    And request body is a multipart file "assets/file.zip"    

---------------

## Files

#### Reading Response body from file
    Given request body from file gadgets/requests/newGadget.json
    When the client performs POST request on /gadgets

#### Reading Response body from file
    When the client performs GET request on /gadgets/12345
    Then response contains properties from file gadgets/responses/createdGadget.json

In the mentioned example, Gadget with ID 12345 is returned in the response and all properties **from createdGadget.json** are searched in response body, values are asserted to be equal.

--------------

## Given

### **Given** Steps in Cukes-REST

| Given Step                                                                                                                                              | Description                                                                                   | Sample                                                                             |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| accept "\<**string**\>" mediaTypes                                                                                                                      | Set header Accept value to a provided **string** for the next request                         | accept "application/json" mediatypes                                               |
| accept mediaType is JSON                                                                                                                                | Set header Accept value to "application/json" for the next request                            | accept mediaType is JSON                                                           |
| authentication type is "\<**string**\>"                                                                                                                 | Set authentication type to provided **string** for current Scenario                           | authentication type is "BASIC"                                                     |
| baseUri is "\<**string**\>"                                                                                                                             | Set base request link to provided **string** for current Scenario                             | baseUri is "http://localhost:8080"                                                 |
| content type is "\<**string**\>"                                                                                                                        | Set header Content-Type value to provided **string** for the next request                     | content type is "application/json"                                                 |
| content type is JSON                                                                                                                                    | Set header Content-Type value to application/json for the next request                        | content type is JSON                                                               |
| cookie "\<**string**\>" with value "\<**string**\>"                                                                                                     | Set required cookie value to provided **string** for the next request                         | cookie "LtpaToken2" with value "abcdefg"                                           |
| formParam "\<**string**\>" is "\<**string**\>"                                                                                                          | Description                                                                                   | Sample                                                                             |
| header "\<**string**\>" with value "\<**string**\>"                                                                                                     | Set required header value to provided **string** for the next request                         | header "If-Match" with value "123"                                                 |
| let variable "\<**string**\>" equal to "\<**string**\>"                                                                                                 | Create variable and assign it provided **string** value for further usage in current Scenario | let variable "gadget" equal to "iPad"                                              |
| param "\<**string**\>" "\<**string**\>"                                                                                                                 | Description                                                                                   | Sample                                                                             |
| proxy settings are "\<**string**\>"                                                                                                                     | Description                                                                                   | Sample                                                                             |
| queryParam "\<**string**\>" is "\<**string**\>"                                                                                                         | Description                                                                                   | Sample                                                                             |
| request body "\<**string**\>"                                                                                                                           | Sets request body to provided **string** for the next request                                 | request body "{"type": "INVALID_TYPE"}"                                            |
| request body from file "\<**string**\>"                                                                                                                 | Sets next request body according to file located in path provided                             | request body from file "gadgets/requests/newGadget.json"                           |
| request body is a multipart file "**\<**string**\>**"                                                                                                   | Description                                                                                   | Sample                                                                             |
| request body: ...                                                                                                                                       | Description                                                                                   | Sample                                                                             |
| resources root is "\<**string**\>"                                                                                                                      | Sets resources root folder path to provided **string** for current Scenario                   | resources root is "cukes-rest-sample\src\test\resources\features\gadgets\requests" |
| session ID "\<**string**\>"                                                                                                                             | Description                                                                                   | Sample                                                                             |
| session ID "\<**string**\>" with value "\<**string**\>"                                                                                                 | Description                                                                                   | Sample                                                                             |
| should wait at most \<**number**\> with interval \<**number**\> until status code \<**number**\>                                                        | Description                                                                                   | Sample                                                                             |
| should wait at most \<**number**\> with interval \<**number**\> until status code \<**number**\> or fail with "\<**string**\>"                          | Description                                                                                   | Sample                                                                             |
| should wait at most \<**number**\> with interval \<**number**\> until property "\<**string**\>" equal to "\<**string**\>"                               | Description                                                                                   | Sample                                                                             |
| should wait at most \<**number**\> with interval \<**number**\> until property "\<**string**\>" equal to "\<**string**\>" or fail with "\<**string**\>" | Description                                                                                   | Sample                                                                             |
| should wait at most \<**number**\> with interval \<**number**\> until header "\<**string**\>" equal to "\<**string**\>"                                 | Description                                                                                   | Sample                                                                             |
| should wait at most \<**number**\> with interval \<**number**\> until header "\<**string**\>" equal to "\<**string**\>" or fail with "\<**string**\>"   | Description                                                                                   | Sample                                                                             |
| username "\<**string**\>" and password "\<**string**\>"                                                                                                 | Sets username and password for requests for current Scenario                                  | username "TestUser" and password "TestPassword"                                    |


---------------

## Headers


#### Adding **Accept** header
    Given accept mediaType is JSON 
      or
    Given accept "application/json" mediaTypes

#### Adding **Content-Type** header
    Given content type is JSON
      or
    Given content type is "application/json"

#### Adding **custom** HTTP header
    Given header Custom-Header with value "custom-value"

#### Copying a value from Response HTTP header
    Given request body from file gadgets/requests/newGadget.json
    When the client performs POST request on /gadgets
    Then let variable "gadgetURL" equal to header "Location" value

#### Asserting headers
    Then header Accept equal to "application/json"

    Then header Accept contains "application/"

    Then header Accept does not contain "invalid-value"

    Then header Custom-Header ends with pattern "d+"

    Then header Invalid-Header is empty

    Then header Accept is not empty

    Then header Accept not equal to "application/xml"
    

---------------

## Parametrized tests

In case you want re-use the same test with different input data or assertions, you might want to take a look at the "Scenario Outline".

If Scenario is defined as "Outline", it must contain "Examples" section under the scenario body.
At the same time, parameters are put into "<>" brackets in the scenario. In the following example, scenario will be executed twice.

    Scenario Outline: Parametrized test

    Given accept "<type>" mediaTypes
    When the client performs GET request on /gadgets
    Then status code is 200
    And header "Content-Type" equal to "<type>"
    
    Examples:
      | type             |
      | application/json |
      | application/xml  |

---------------

## Then

### **Then** Steps in Cukes-REST

| Then Step                                                                          | Description | Sample |
|:-----------------------------------------------------------------------------------|:------------|:-------|
| a failure is expected                                                              | Description | Sample |
| header "**\<string\>**" contains "**\<string\>**"                                  | Description | Sample |
| header "**\<string\>**" does not contain "**\<string\>**"                          | Description | Sample |
| header "**\<string\>**" ends with pattern "**\<string\>**"                         | Description | Sample |
| header "**\<string\>**" equal to "**\<string\>**"                                  | Description | Sample |
| header "**\<string\>**" is empty                                                   | Description | Sample |
| header "**\<string\>**" is not empty                                               | Description | Sample |
| header "**\<string\>**" not equal to "**\<string\>**"                              | Description | Sample |
| it fails with "**\<string\>**"                                                     | Description | Sample |
| let variable "**\<string\>**" equal to header "**\<string\>**" value               | Description | Sample |
| let variable "**\<string\>**" equal to property "**\<string\>**" value             | Description | Sample |
| response body does not contain "**\<string\>**"                                    | Description | Sample |
| response body not equal to "**\<string\>**"                                        | Description | Sample |
| response contains "**\<string\>**"                                                 | Description | Sample |
| response contains an array "**\<string\>**" of size "**\<string\>**"               | Description | Sample |
| response contains properties from file "**\<string\>**"                            | Description | Sample |
| response contains properties from json: ...                                        | Description | Sample |
| response contains property "**\<string\>**" containing phrase "**\<string\>**"     | Description | Sample |
| response contains property "**\<string\>**" matching pattern "**\<string\>**"      | Description | Sample |
| response contains property "**\<string\>**" not matching pattern "**\<string\>**"  | Description | Sample |
| response contains property "**\<string\>**" of type "**\<string\>**"               | Description | Sample |
| response contains property "**\<string\>**" with value: ...                        | Description | Sample |
| response contains property "**\<string\>**" with value "**\<string\>**"            | Description | Sample |
| response contains property "**\<string\>**" with value other than "**\<string\>**" | Description | Sample |
| response does not contain property "**\<string\>**"                                | Description | Sample |
| response equals to "**\<string\>**"                                                | Description | Sample |
| response is empty                                                                  | Description | Sample |
| response is not empty                                                              | Description | Sample |
| status code is **\<number\>**                                                      | Description | Sample |
| status code is not **\<number\>**                                                  | Description | Sample |


---------------

## When

### **When** Steps in Cukes-REST

When Step | Description | Sample
------------ | ------------- | -------------
the client performs **\<string\>** request on "**\<string\>**" | Performing an HTTP request of a given Method, enriched with conditions in Given steps | the client performs GET request on "/gadgets"

---------------

## Variables

#### Setting and Reading a value
    Given let variable "gadgetId" equal to "12345"
    When the client performs GET request on /gadgets/{(gadgetId)}

#### Accessing Response HTTP headers as a variable
    When the client performs GET request on /gadgets
    Then response contains property "gadgets[0].id" of value "{(header.GadgetId)}"

#### Copying a value from Response HTTP header
    Given request body from file gadgets/requests/newGadget.json
    When the client performs POST request on /gadgets
    Then let variable "gadgetURL" equal to header "Location" value

#### Copying a value from property from Response body
    Given request body from file gadgets/requests/newGadget.json
    When the client performs POST request on /gadgets
    Then let variable "gadgetOwner" equal to property "owner" value

#### Variables in files

If reading request from file, Cukes-REST will automatically substitute variable placeholders with appropriate values if defined.

Example:

    {
        "name" : "{(gadgetName)}"
    }

and scenario

    Given request body from file gadgets/requests/newGadget.json
    And let variable "gadgetName" equal to "iPhone"
    When the client performs POST request on /gadgets
    
---------------    