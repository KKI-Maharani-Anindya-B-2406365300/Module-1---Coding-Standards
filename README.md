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

Module 3 

Reflection 
1. Explain what principles you apply to your project!
   1) SRP
      Application in the project :
      In this project, responsibilities are clearly separated across different layers. ProductController is responsible only for handling HTTP requests related to products, while CarController handles requests related to cars. ProductServiceImpl focuses on implementing the business logic for products, and ProductRepository is dedicated to managing product data storage. The same structure and separation are consistently applied to the Car classes. Each layer has a distinct responsibility: the Controller manages the web interaction, the Service handles the business logic, the Repository manages data access and storage. By separating concerns in this way, each class has only one reason to change, which aligns with the Single Responsibility Principle.
   2) OCP
      Application in the project:
      The project is designed so new entities can be added by creating new Controller, Service, and Repository classes without modifying existing Product or Car classes. Additionally, services depend on interfaces (ProductService, CarService), allowing behavior to be extended by creating new implementations without modifying existing code.
   3) LSP
      Application in the project:
      Previously, controllers were incorrectly using inheritance. After refactoring, CarController no longer extends ProductController, eliminating improper substitution. Each class now has its own clear hierarchy and does not misuse inheritance, ensuring that no subclass violates expected behavior.
   4) ISP
      Application in the project:
      Instead of having one large service interface, the project uses: ProductService, CarService. Each interface only contains methods relevant to that specific entity. This prevents unnecessary dependencies and keeps interfaces small and focused.
   5) DIP
      Application in the project:
      Controllers depend on service interfaces (ProductService, CarService) instead of concrete implementations. Dependency injection (constructor injection) is used to inject implementations into controllers and services. This minimizes dependencies between components and makes the system more flexible, maintainable, and easier to test.

2. Explain the advantages of applying SOLID principles to your project with examples.
Applying SOLID principles made the project much more organized and easier to manage.
    1) it improved code clarity and structure. Because we separated the Controller, Service, and Repository layers, each class has a clear role. For example, ProductController only handles web requests, while ProductServiceImpl focuses on business logic. This makes the code easier to read and understand since we always know where certain logic belongs.
    2) it made the system easier to maintain and modify. If we need to change how products are stored, we only modify the repository layer without touching the controller or service. Similarly, if we want to change the business logic (for example, adding validation before creating a product), we only update the service layer. This reduces the risk of breaking other parts of the system.
    3) it makes the project easier to extend. For example, when adding the Car feature, we did not need to modify the Product classes. We simply created CarController, CarService, and CarRepository. The existing Product module remained untouched. This shows how the project is open for extension but closed for modification.
    4) it improves testability. Because controllers depend on service interfaces instead of concrete classes, we can easily replace real services with fake or mock implementations during testing. This makes unit testing simpler and more reliable.

3. Explain the disadvantages of not applying SOLID principles to your project with examples.
If SOLID principles were not applied in this project, the code would become harder to manage and more prone to errors over time.
    1) without SRP, a class could handle multiple responsibilities at once. For example, if ProductController also handled business logic and data storage, any small change in database logic could affect the web layer. This would make the class harder to understand and maintain, since one change could break unrelated functionality.
    2) without OCP, adding new features would require modifying existing code. For instance, when adding the Car feature, we might have needed to modify the Product classes instead of creating separate ones. This increases the risk of introducing bugs into code that was already working.
    3) without LSP, improper inheritance could cause unexpected behavior. For example, if CarController extended ProductController, it would inherit behaviors that might not fully apply to cars. This could lead to inconsistent logic or runtime errors.
    4) without ISP, we might create one large service interface containing all methods for every entity. Classes would then be forced to implement methods they do not use, making the design messy and harder to maintain.
    5) without DIP, Controllers would rely directly on specific implementation classes instead of abstractions. As a result, the components would become tightly connected, making the system harder to modify or extend without affecting other parts.























      
