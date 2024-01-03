## Journal

### Date: [03.01.2024]

#### Objective: Implement robust security measures within the project, including user authentication and authorization.

#### Challenges Faced:

- **"Bad credentials" Error:**
  - Encountered persistent "Bad credentials" errors during user authentication.
  - **Resolution:** Investigated the authentication flow, focusing on password encoding and comparison processes. Verified the password encoding algorithm consistency between user registration and authentication. Finally, corrected the encoding algorithm and ensured that stored passwords matched the format expected by the PasswordEncoder.

- **Dependency Injection Issues:**
  - Faced problems with dependency injection within the SecurityConfig class.
  - **Resolution:** Refactored the SecurityConfig class to utilize constructor injection for UserRepository and PasswordEncoder. Ensured proper initialization and injection of dependencies by annotating the constructor parameters, resolving issues related to null dependencies and misconfiguration.

- **Integration of UserDetailsService with UserService:**
  - **Challenge:** Integrating the UserDetailsService with the UserService while maintaining a connection to the UserRepository.
  - **Resolution:** Altered the UserService to implement UserDetailsService, allowing it to fetch user details from the UserRepository. Ensured the correct wiring of dependencies by using constructor injection, facilitating a seamless interaction between the services and repository.

- **Authentication Failure Investigation:**
  - **Issue:** Investigated cases of failed authentication despite correct credentials.
  - **Resolution:** Employed thorough logging and debugging techniques to trace the authentication process. Logged key authentication-related values, such as entered passwords, encoded passwords, and stored hashed passwords, to pinpoint discrepancies. Adjusted password encoding and comparison logic to ensure consistency.
