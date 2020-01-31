#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@sanity
Feature: validation of home page related scenarios

  Scenario: Verify Brand Logo navigation in Home page
    Given user is on homepage
    Then user validates successful navigation to home page
    Then user writes the results

  Scenario: Verify the promotional slot redirections
    Given user is on homepage
    Then user validates promotions and its redirections
    Then user writes the results

  Scenario Outline: Verify Search box redirections
    Given user is on homepage
    When user enters <keyword> into search box and clicks on enter to search
    Then user writes the results

    Examples: home page data
      | keyword |
      | tops    |

  Scenario: Verify L1 Categories
    Given user is on homepage
    Then user verifies L1 categories
    Then user writes the results
