
@Athletes
Feature: Athletes Rest API testing

  Background: 
    Given User Setup the base uri "obs.base.uri"

  @SC01_GetAthletes
  Scenario: SC01_Get_All_Athletes_participating_in_Olympic
    Then Setup request endpoint by using base path "obs.base.path.athletes" for request "GetAthletes"
    When User sends a "GET" request to retrive data
    Then Response status code should match with expected status code 200
    And The Response Json schema should match with expected schema from file "GetAthletes"
    And The Response body athletes data should match with data from database table "table.name.athlete" and query "athlete.query"

  @SC02_GetAthlete_By_Id
  Scenario: SC02_Get_An_Athlete_participating_in_Olympic_by_athleteId
    Then Setup request endpoint by using base path "obs.base.path.athletes.by.id" for request "GetAthlete_By_Id"
    When User sends a "GET" request to retrive data
    Then Response status code should match with expected status code 200
    And The Response Json schema should match with expected schema from file "GetAthleteById"
    And The Response body athletes data should match with data from database table "table.name.athlete" and query "athlete.query.by.id"

  @SC03_GetGames
  Scenario: SC03_Get_games_in_Olympic
    Then Setup request endpoint by using base path "obs.base.path.games" for request "GetGames"
    When User sends a "GET" request to retrive data
    Then Response status code should match with expected status code 200
    And The Response Json schema should match with expected schema from file "GetGames"
    And The Response body athletes data should match with data from database table "table.name.game" and query "game.query"
