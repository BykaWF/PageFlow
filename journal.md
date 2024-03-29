## Journal

### Date: 03.01.2024

1. **"Bad credentials" Error:**
    - **Problem:** Kept getting errors saying "Bad credentials" when users tried to log in.
    - **Solution:** Checked how passwords were being checked. Found a mistake in how passwords were being stored and checked. Fixed the issue to ensure passwords were checked properly.

2. **Dependency Injection Issues:**
    - **Problem:** Had trouble making parts of the project talk to each other.
    - **Solution:** Changed how some parts of the project were set up so they could share information more easily. This fixed the communication problem.

3. **Integration of UserDetailsService with UserService:**
    - **Challenge:** Needed to connect different parts of the project to handle user information correctly.
    - **Solution:** Changed a part of the project to work better with another part. Made sure they understood each other properly, which helped manage user details more smoothly.

4. **Authentication Failure Investigation:**
    - **Issue:** Some users couldn't log in even with the right password.
    - **Solution:** Checked step by step how users were trying to log in. Found where things were going wrong. Made changes to how passwords were being handled to make sure everything matched up properly.

### Date: 08.01.2024

Change to Constructor Dependency Injection:

 - **Reason**: After referencing the official Spring framework documentation, opted to switch from using @Autowired annotation to Constructor Dependency Injection for improved code readability, testability, and reduced coupling.
 - **Impact**: This change enhances and ensures better management of dependencies, based on practices recommended by Spring.

### Date: 5.02.2024

1. **Authentication Failure Investigation**:

    - **Issue**: Some users couldn't log in even with the right password.
    - **Solution**: Checked step by step how users were trying to log in. Found where things were going wrong. Made changes to how passwords were being handled to make sure everything matched up properly.

2. **Connection between Frontend and Backend**:

    - **Challenge**: Integrating frontend developed using JavaScript, HTML, CSS, and Bootstrap with backend functionalities.
    - **Solution**: Implemented RESTful APIs to facilitate communication between the frontend and backend components, ensuring seamless data exchange.

3. **Added JWT Token Authentication**:

    - **Challenge**: Enhancing security by implementing JWT token authentication for user sessions.
    - **Solution**: Integrated JWT token authentication mechanism into the backend, allowing secure user authentication and authorization processes.
  
### Date: 11.02.2024

Added search-book.html with a search engine using PostgreSQL Full Text:

   - **Challenge**: Implementing a search functionality for books.
   - **Solution**: Created search-book.html and implemented a search engine using PostgreSQL Full Text search for efficient book searching.

Date: 20.02.2024

After making a payment, clear shopping session and cart_item:

    Challenge: Ensure that the shopping session and cart items are properly cleared after a payment is made.
    Solution: Implemented logic to clear the shopping session and cart items upon successful payment.

Save all required info in Transaction:

    Challenge: Need to save all necessary information related to transactions.
    Solution: Implemented functionality to save all required information in the Transaction entity.

Change implementation of Transaction:

    Challenge: Needed to change the implementation of transactions for improved functionality.
    Solution: Modified the implementation of transactions to enhance their functionality.

Implement adding cart_item from frontend:

    Challenge: Implement functionality to add cart items from the frontend.
    Solution: Developed the frontend functionality to allow users to add items to their shopping carts.

Implement frontend view of shopping cart:

    Challenge: Create a user-friendly frontend view for the shopping cart.
    Solution: Designed and implemented a frontend view for the shopping cart to enhance user experience.

Transaction moments:

    Challenge: Implement transaction moments for better tracking and management.
    Solution: Added functionality to track transaction moments for improved management and monitoring.

See in profile books:

    Challenge: Enable users to view their purchased books in their profiles.
    Solution: Implemented functionality to display purchased books in user profiles.

See in profile transaction info (only price and when):

    Challenge: Display transaction information, including price and timestamp, in user profiles.
    Solution: Added functionality to display transaction information, including price and timestamp, in user profiles.

Date: 29.02.2024

Update quantity (algorithm for checking same items and changing quantity for current student):

    Challenge: Implement an algorithm to update the quantity of items in the shopping cart for the current student.
    Solution: Developed an algorithm to check for identical items and update their quantities accordingly for the current student.

Solved problem with transactions and cartItems:

    Challenge: Addressed issues related to transactions and cart items.
    Solution: Identified and resolved problems related to transactions and cart items for improved functionality.

Price $null:

    Challenge: Resolve issues related to displaying null prices.
    Solution: Implemented a fix to display prices properly, including handling cases where prices were null.

This journal-style summary provides an organized overview of the to-do tasks completed on different dates, along with the challenges faced and the solutions implemented.
