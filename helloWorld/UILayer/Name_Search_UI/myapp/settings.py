"""Settings for our application

Any variable defined in ALL_CAPS in this file will be read by our app and
interpreted as a configuration parameter.

Below we read in configuration values from environment variables with the same names.
"""

import os

SECRET_KEY = os.getenv('SECRET_KEY')

DEBUG = os.getenv('DEBUG', default=False)
if DEBUG == 'True':
    DEBUG = True




