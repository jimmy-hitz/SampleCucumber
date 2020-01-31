Feature: Checkout Order Placement

  Scenario Outline: Validation of UseCase Guest User Order Placement flow from PLP
    Given User is on Brand Home Page <Site>
    When Clicking on Super & Sub Category shall take user to PLP Page

    Examples: Checkout User Information
      | Site  | EmailID   | FName     | LName     | AddL1     | ZipCode   | PhoneNo   | CCNo      | CCMonth   | CCYear    | CVV       |
      | Site5 | TestData2 | TestData2 | TestData2 | TestData2 | TestData2 | TestData2 | TestData2 | TestData2 | TestData2 | TestData2 |
 