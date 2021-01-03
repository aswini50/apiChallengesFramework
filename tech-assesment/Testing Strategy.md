Testing Strategy for Under Developed FrontEnd Application:

- Purpose 
	- This document attempts to describe the test strategy that will be followed by automation tester to automate front-end of the application. 

- Scope of Testing
	- Automated Smoke and Regression test scripts development, execution and maintenance

- Front-End application Technology Stack
	- React JS

- Automation Tool Evaluation
	- Shortlist couple tools which supports React JS application based automation
	- Recommended Cypress, Selenium, Protractor

- Entry Criteria

	- Entry in Sprint 1
	- Get the Stories/requirements from PO team
	- Walk through and understanding the stories
	- Daily sync up with Development to understand the design and architecture

	- Application Ready - A minimum viable product

		- In Sprint 2, assuming Sprint 1 developed and deployed with a mvp.
		- Will start understanding the application by doing manual walk through
		- Understand the different components of the UI application
		- Discuss with developer to understand the integration approach for Rest API , database etc..
		- Understand the functional test cases and their validation points

	- Star Automation POC

		- Plan and implement POC by designing couple of test cases using different automation tool
		- Finalize the automation tool and complete the POC

	- Star Automation Testing

		- Start designing automation framework

		- Develop automation scripts for Sprint 1 stories - Talk to Developer on Daily basis
			- Maintain good understanding with developers
			- To understand the application functionality change implementation
			- How they are designing the CSS and html pages
			- Understand the html dom components are dynamic or static
			- Inform developers to provide unique html attribute for each html attributes
			- This will help the automation team to develop scripts faster and less prone to maintenance

		- Complete the development and start executing the scripts against sprint 1 code base

			- publish the execution report
			- Log automation defects
			- Follow up with developer to understand the impact of defect fixes
			- Once defects fixed , re-execute automation scripts

- Exit Criteria

	- Zero Automation Open Defects
	- Execution Reports stored or uploaded again respective Story
	- All automation code committed to version control system
	- Status update to higher management
	- Sprint sign-off

- Overall Remark

	- The Rest API Response Json should be Json object instead of Json array
	- Authorization and Authentication mechanism implementation for Rest API
	  