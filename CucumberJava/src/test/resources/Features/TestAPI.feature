Feature: Test All the API Under https://gorest.co.in/
	
  Scenario: Verify All the API
    And Hit All User API and verify the details

  Scenario: Verify User API with valid id
    And Hit an Inidividual user API with valid id
    
  Scenario: Verify User API with invalid id
    And Hit an Inidividual user API with invalid id
    
  Scenario: Verify Create User API with valid Access Token
    And Hit Create an Inidividual user API with valid Access Token

  Scenario: Verify Create User API with invalid Access Token
    And Hit Create an Inidividual user API with invalid Access Token
    
  Scenario: Verify Create User API with expired Access Token
    And Hit Create an Inidividual user API with expired Access Token
    
  Scenario: Verify Update User API with valid Access Token
    And Hit Update an Inidividual user API with valid Access Token
    
  Scenario: Verify Update User API with invalid Access Token
    And Hit Update an Inidividual user API with invalid Access Token    
    
  Scenario: Verify Update User API with expired Access Token
    And Hit Update an Inidividual user API with expired Access Token      
       
  Scenario: Verify Delete User API with valid Access Token with valid user id
    And Hit Delete an Inidividual user API with valid Access Token with valid user id
    
  Scenario: Verify Delete User API with invalid Access Token with valid user id
    And Hit Delete an Inidividual user API with invalid Access Token with valid user id    
    
  Scenario: Verify Delete User API with expired Access Token with valid user id
    And Hit Delete an Inidividual user API with expired Access Token with valid user id  
      
  Scenario: Verify Delete User API with valid Access Token with invalid user id
    And Hit Delete an Inidividual user API with valid Access Token with invalid user id  
         