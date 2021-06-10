@login @ULS01 @regression @smoke @sprint1 @firefox
Feature: Userstory_ULS01_Login

@usernameerrors @logintest 
  Scenario Outline: Validate username errors
    Given I Navigate to "http://automationpractice.com/index.php?controller=authentication&back=my-account" in the browser
    And I wait for "5" seconds
    And I validate username textbox with form having "signin.begin_form_username_class" class
    And I enter "<user>" into "signin.username" textbox
    And I enter "<password>" into "signin.password" textbox
    And I validate username textbox with form having "signin.invalid_username_class" class
    And I click on "signin.submit" link
    And I wait for "10" seconds
    Then I validate "username" error with alert danger

    Examples: 
      | user        | password |
      | adsas       | test123  |
      | adsas@      | test123  |
      | adsas@gmail | test123  |
      | @gmail.com  | test123  |
      | .com        | test123  |

  
  @passworderrors @logintest
  Scenario Outline: Validate empty password errors
    Given I Navigate to "http://automationpractice.com/index.php?controller=authentication&back=my-account" in the browser
    And I wait for "5" seconds
    And I validate username textbox with form having "signin.begin_form_username_class" class
    And I enter "<user>" into "signin.username" textbox
    And I enter "<password>" into "signin.password" textbox
    And I click on "signin.submit" link
    And I wait for "10" seconds
    Then I validate "emptypassword" error with alert danger

    Examples: 
      | user                  | password |
      | tester123@testing.com |          |
      
        @passworderrors @logintest
  Scenario Outline: Validate empty password errors
    Given I Navigate to "http://automationpractice.com/index.php?controller=authentication&back=my-account" in the browser
    And I wait for "5" seconds
    And I validate username textbox with form having "signin.begin_form_username_class" class
    And I enter "<user>" into "signin.username" textbox
    And I enter "<password>" into "signin.password" textbox
    And I click on "signin.submit" link
    And I wait for "10" seconds
    Then I validate "invalidpassword" error with alert danger

    Examples: 
      | user                  | password |
      | tester123@testing.com | t        |
      | tester123@testing.com | tttt     |

  @authenticationerrors @logintest
  Scenario Outline: Validate Authentication failed msgs
    Given I Navigate to "http://automationpractice.com/index.php?controller=authentication&back=my-account" in the browser
    And I wait for "5" seconds
    And I validate username textbox with form having "signin.begin_form_username_class" class
    And I enter "<user>" into "signin.username" textbox
    And I enter "<password>" into "signin.password" textbox
    And I click on "signin.submit" link
    And I wait for "10" seconds
    Then I validate "authentication" error with alert danger

    Examples: 
      | user                      | password   |
      | testautoskills@tester.com | abcdef     |
      | tester123@testing.com     | Tester123@ |

      
  @validlogin @logintest
  Scenario Outline: Validate Authentication failed msgs

	Given I Navigate to "http://automationpractice.com/index.php?controller=authentication&back=my-account" in the browser
  And I wait for "5" seconds
  And I validate username textbox with form having "signin.begin_form_username_class" class
  And I enter "<user>" into "signin.username" textbox
  And I enter "<password>" into "signin.password" textbox
  And I click on "signin.submit" link
  And I wait for "10" seconds
  Then I should see "Welcome to your account. Here you can manage all of your personal information and orders." on the page
   

Examples:
  |user												|password						|
  |testautoskills@tester.com	|	Tester123@				|     