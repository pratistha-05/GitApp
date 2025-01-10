# GitApp

GitApp is an Android application that allows users to search for GitHub users and view their repositories. This app integrates with the GitHub API to fetch user data, display user profiles, and allow navigation to repositories.
***To run this App you would require Github personal acess token for authentication purpose for APIs to fetch data. Please generate your token from https://github.com/settings/personal-access-tokens***

## Features

- **Search GitHub Users**: Allows users to search GitHub users by their usernames.
- **View User Repositories**: Upon selecting a user, the app displays a list of their repositories.
- **Pagination**: Implements pagination for both the user search results and repositories.
- **Empty State Handling**: Displays a "No Results Found" message when no users are found.
- **Pull to Refresh**: Users can refresh the list of users by pulling down on the list.
  
## Tech Stack

- **Kotlin**: Programming language used for the app.
- **Retrofit**: Networking library to make API calls to GitHub.
- **ViewModel**: Architecture component for managing UI-related data.
- **LiveData**: Reactive data holders for managing UI updates.
- **Coroutines**: Asynchronous programming with Kotlin's Coroutines.

## Setup
1.  **Open the project in Android Studio:**
After cloning the repository, open the project in Android Studio. You can do this by either:
    
    *   Launching Android Studio, then selecting **Open** and navigating to the folder where you cloned the repository.
        
    *   Or directly opening the GitApp folder in Android Studio if you have already set up the environment.
        
2.  You can create a GitHub Personal Access Token [here](https://github.com/settings/tokens).
    
    *   In your project root directory, create a new file named gradle.properties (if not already present).
        
    *   properties : Copy code GITHUB\_AUTH\_TOKEN=your-github-token
        
3.  **Sync Gradle:**
    
    *   In Android Studio, once the project is open, you should see a prompt to **Sync Now** with Gradle. If you don’t see it, manually click **File > Sync Project with Gradle Files** to sync the project.
        
4.  **Build the project:**
    
    *   Once Gradle sync is complete, you can build the project by clicking on the **Build** option in the top menu and selecting **Build Project**.
        
5.  **Run the application:**
    
    *   After a successful build, you can run the app on an emulator or a physical Android device. Select **Run > Run 'app'** or press the **Shift + F10** keys to start the app.
        
6.  **Start interacting with the app:**
    
    *   The app will open up, allowing you to search for GitHub users and view their repositories.
        

## Screenshots
<p align="center">
  <img width="200" alt="Screenshot 2025-01-10 at 12 00 33 PM" src="https://github.com/user-attachments/assets/0aadd4cd-c75f-46e2-bef3-c0a8e64eaecb" style="margin-right: 20px;" />
  <img width="200" alt="Screenshot 2025-01-10 at 12 01 19 PM" src="https://github.com/user-attachments/assets/d8ba2e33-0e01-4edb-aef1-53d6472d7ebb" style="margin-right: 20px;" />
  <img width="200" alt="Screenshot 2025-01-10 at 12 01 43 PM" src="https://github.com/user-attachments/assets/81239be8-979d-4cab-8c9e-f0e3f69465bb" style="margin-right: 20px;"/>
  <img width="200" alt="Screenshot 2025-01-10 at 12 10 00 PM" src="https://github.com/user-attachments/assets/5a6eb613-f462-4eb5-a021-9443f4d0dcac" />

</p>


