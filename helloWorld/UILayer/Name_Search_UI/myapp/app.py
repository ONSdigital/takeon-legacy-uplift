import os
import json
import requests

# Note that certain objects in flask (session, current_app and request) are actually proxies to
# other objects bound to a particular thread. When accessing these object you can be
# sure its for the same thread that is running the application/request/session etc. If instead
# we used global variables for something like this, these variables would not be thread safe.
# More details at http://flask.pocoo.org/docs/0.12/quickstart/#context-locals
from flask import Flask, render_template, session, current_app, request

from myapp import settings
from myapp.forms import SearchForm


APP_DIR = os.path.dirname(__file__)

app = Flask(__name__)
app.config.from_object(settings)


@app.route('/')
def hi():
    """Root endpoint """
    return "Collection team are great!"

# Needed to add GET and POST methods as Flask only uses GET by default
@app.route('/dashboard', methods=['GET', 'POST'])
def dashboard():
    """Our main dashboard interface page to display the name data

    This endpoint displays a table of data in html that is loaded from the Business Layer

    """

    # Create object of SearchForm (flaskform) from forms.py
    form = SearchForm()
    # API call [GET]
    records = load_service()
    current_app.logger.info(f'Records: {records}')


    record_for_header_titles = records[0]
    # Get headers from first dictionary/row
    # Same in all dictionaries so just using first one
    header_titles = []
    for key in record_for_header_titles:
        header_titles.append(key)


    if form.validate_on_submit():

        # Get value from search bar flask form
        first_name = form['first_name'].data
        current_app.logger.info(f'First Name: {first_name}')

        search_data = requests.get(f'http://localhost:8090/Person/FindPersonByFirstName/{first_name}')
        found_records = json.loads(search_data.text)
        # Check for 'error' in passed JSON and append this to a list to be handled on UI
        if 'error' in found_records:
            records = []
            records.append(found_records.copy())
            record_for_header_titles = records[0]
            # Reload header titles as the GET calls return different headers
            header_titles = []
            for key in record_for_header_titles:
                header_titles.append(key)

            current_app.logger.info(f'Records: {records}')
        # Also check if records is not blank as it returns an empty list
        elif (found_records and 'error' not in found_records):

            records = found_records
            # Get headers from first dictionary/row
            record_for_header_titles = records[0]
            # Same in all dictionaries so just using first one
            # Reload header titles as the GET calls return different headers
            header_titles = []
            for key in record_for_header_titles:
                header_titles.append(key)
        else:
            records = found_records

        current_app.logger.info(f'Records: {records}')
        current_app.logger.info(f'Header: {header_titles}')

    return render_template('dashboard.html', header=header_titles, records=records,form=form)


def load_service():
    """Json being returned from Java API"""

    """ Call to API """
    name_data = requests.get('http://localhost:8090/Person/returnAll')
    data_row = json.loads(name_data.text)
    return data_row
