# Name Search UI

## Quick Start

1. Clone this repository and navigate to the root directory on the command line

2. Install the dependencies with `pip install -r requirements.txt`

3. Set the following environment variables:
```
FLASK_APP=myapp/app.py
FLASK_DEBUG=1
SECRET_KEY='' # Whatever you want as this is for testing purposes only
```

4. Run the application with `flask run`

## Using a `env.bat` File

In windows to make step 3 above easier you can create a `env.bat` file that sets these variables for the current active
terminal session:

```
@ECHO OFF
set FLASK_APP=myapp/app.py
set FLASK_DEBUG=1
set SECRET_KEY='RYAN123' #Example
```

This can be executed simply by running `env.bat`.

Following the 12 factor app, we will be storing configuration in environment variables, which later will include secret
information. So *make sure that this file is never submitted to version control*
