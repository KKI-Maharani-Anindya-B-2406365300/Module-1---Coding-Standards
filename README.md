Module 1

Reflection 1 

In this exercise, I implemented two new features in the eShop application using Spring Boot: Edit Product and Delete Product. While working on these features, I tried to apply the clean code practices that were taught in this module. I structured the application using a layered approach by separating the code into controller, service, repository, and model so that each part has a clear responsibility. This made the code easier to read, understand, and maintain. I also used clear and descriptive method names such as create, findAll, and deleteById, which helped make the program flow more intuitive. In terms of security, I avoided hard-coding any sensitive information in the source code and implemented the delete feature using a POST request instead of a GET request to reduce the risk of accidental deletion. A confirmation dialog was also added in the user interface to prevent users from deleting data unintentionally. However, after reviewing the code, I noticed some areas that could be improved. Most of the input validation is currently handled only on the client side through HTML attributes, so adding server-side validation would make the application more robust. Additionally, the application still uses in-memory storage, meaning all data is lost when the application restarts. Using a database with Spring Data JPA would be a better approach for a real-world application. Overall, this exercise helped me better understand how to implement new features in an organized way while being mindful of code quality and basic security considerations.

Reflection 2

1. After writing the unit tests, I felt more confident about my code because the tests helped me verify that each feature worked as expected, especially for different situations like valid and invalid inputs. Writing unit tests also made me more aware of edge cases that I might not have considered when implementing the features. There is no fixed number of unit tests that must be written in a class, but ideally there should be enough tests to cover the main functionality, edge cases, and possible failure scenarios without being redundant. Having 100% code coverage does not guarantee that the code is free from bugs or errors, because tests might not check the correctness of the logic in all situations. Good tests focus on real behavior and edge cases, not just on achieving high code coverage. High coverage alone doesn’t guarantee quality if the tests don’t actually catch real problems.

2. I believe the test will still work, but the code becomes less clean because of repeated logic. This duplication makes the tests harder to maintain, since any change to the setup would need to be updated in multiple places, and it also reduces readability by adding a lot of repetitive setup code. A better way to handle this is to move shared setup into a base test class or helper methods, and use consistent selectors or simple page-object helpers so each test focuses more on its behavior. This keeps the functional test suite cleaner, easier to maintain, and easier to extend.

Module 2

Reflection 
1. - ProductController
    removed unused import statements to improve readability and mantain clean code
   - ProductRepository 
     added missing braces to control statements to improve code consistency
   - EshopApplication
     adjusted the class structure to satisfy PMD rules related to utility classes while keeping it compatible with Springboot
     each issue was comitted separately to maintain clear version history, I confirmed that the issues were resolved in the next workflow runs.

2. Yes, the current implementation has met the definition of Continuous Integration and Continuous Deployment. CI is implemented because every push or merge to the main branch automatically triggers GitHub Actions to run build and analysis workflows, allowing the code to be continuously tested and validated. CD is implemented because after a successful merge to the main branch, render automatically pulls the latest commit and deploys the application. thus, the pipeline makes both the integration and deployment processes run smoothly and automatically, which reflects the main idea behind CI/CD.
