# Introduction - Wearables Portal

This Werables Portal application development is includes redesigning the HPN Wearable’s existing vet and management portal and enhancing their usability with the implementation of additional features. Unlike the existing implementation, the portal will have a single login for both management and vet portals, with  functionality access defined on the basis of the logged in user’s role.


## Setup

* Step 1 - Clone a Git repository - 
	Open Eclipse and on the top-right, you will find all the perspectives like Java, Java EE, debug, etc. Search for Git and click on it. Click on the clone icon to clone a Git repository and add the clone to this view. Git repositories will be empty to start.
* Step 2 - Choose the source Git repository - 
	The window below will be appear where we choose our source Git repo. Copy your repository's HTTPS link and paste it in the window.  Once you paste the validated URI, the rest of the fields will get auto-filled. Then click Next.
* Step 3 - Select the branches that need to clone - 
	Then the Branch Selection window will appear. Just select the branches you want to clone from the repository. Then click Next.
* Step 4 - Storing the Cloned Repository - 
	Select directory to store the Cloned Repository. Select Initial Branch like Master or Branch1 or Branch2. Click Finish

* Now we have successfully configured Git on Eclipse! You can import a Git project into Eclipse and perform operations.

### CloudRun Deployment

To install on Cloud Run:

* Service account key to be added to repo
* Change GOOGLE_APPLICATION_CREDENTIALS in Dockerfile to point to correct service account key.
* Follow Cloud Run quickstart to set up an instance, create project directory

* Pull code from Github repo (https://bitbucket.org/ctepl/wearables_services/)
* Set `$PROJECT_ID`, `$SERVICE_NAME`, and `$DOCKER_IMG`:

`SERVICE_NAME="wp-service-develop2"`

`PROJECT_ID=$(gcloud config get-value project)`

`DOCKER_IMG="gcr.io/$PROJECT_ID/$SERVICE_NAME"`

* Build and push docker image to Google Artifact Registry (GAR)

`docker build -t us-central1-docker.pkg.dev/ct-wearables-portal-pf/wearables-portal-dev2/wearables-portal-dev2:$BRANCH-$BITBUCKET_COMMIT_SHORT --build-arg   SPRING_PROFILE=$BRANCH .`

`docker push us-central1-docker.pkg.dev/ct-wearables-portal-pf/wearables-portal-dev2/wearables-portal-dev2:$BRANCH-$BITBUCKET_COMMIT_SHORT`

* Deploy and run docker image

`gcloud run deploy wp-service-$BRANCH --region=us-central1 --image=us-central1-docker.pkg.dev/ct-wearables-portal-pf/wearables-portal-dev2/wearables-portal-dev2:$BRANCH-$BITBUCKET_COMMIT_SHORT --service-account=wms-sa@ct-wearables-portal-pf.iam.gserviceaccount.com --allow-unauthenticated `

* Deployed address of development instance

	https://wp-service-develop2-ygue7fpaba-uc.a.run.app/wearables

## PUT request to reset the password calling the '/users/forgotPassword' route

* Example:

  `curl -X PUT "http://wp-service-develop2-ygue7fpaba-uc.a.run.app/wearables/api/users/forgotPassword?userName=abcd@ctepl.com" -H "accept: application/json"`


## Input

The application operates on receipt of a put request. The put request should have the following content in query parameter

format:
<table class="tg">
<thead>
  <tr>
    <th class="tg-0pky">Parameter</th>
    <th class="tg-0pky">Description</th>
  </tr>
</thead>
<tbody>
  <tr>
    <th class="tg-0pky">userName</th>
    <th class="tg-0pky">Login name of the user</th>
  </tr> 
  <tr>
    <th class="tg-0pky">modifiedBy (Optional)</th>
    <th class="tg-0pky">User id of the admin user</th>
  </tr>    
</tbody>
</table>

## Output

The application checks with the database for the userName. If the userName is available the system generates a temporary password and saves it in the database and sends this temporary password to the user's email. If the userName is not available the system will send an error message with status 404

## Release Notes

## Known Issues
