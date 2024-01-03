## Journal

### Date: [03.01.2024]

### Objective: 
Ensure strong security for the project, especially regarding user login and access.

### Challenges Faced:

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

