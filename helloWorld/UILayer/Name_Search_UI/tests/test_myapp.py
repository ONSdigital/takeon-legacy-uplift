
import pytest
from flask import json, jsonify

# If the tests discovered are part of a python package, the parent directory of that
# package is added to the PATH. We make use of this by adding an __init__.py file that
# makes this test directory a package. Therefore pytest added the flask-workshop directory to the PATH
# which means it can find the myapp package.
from myapp.app import app, load_service


# Pytest fixtures are a way of specifying setup resources to be used by multiple tests
# More info at https://docs.pytest.org/en/latest/fixture.html
# So works well for testing multiple endpoints for example
@pytest.fixture()
def test_client():
    """The flask test client for our app.

    This lets us trigger http requests to end points without needing a server running.
    """

    app.testing = True
    client = app.test_client()

    return client


# Below we use pytest to parametrize our tests for endpoints
# More details at https://docs.pytest.org/en/latest/parametrize.html
@pytest.mark.parametrize('url,target', [
    ('dashboard', 'Dashboard'),
    ('/', 'Collection'),
])
def test_endpoints(url, target, test_client):
    """ Given an endpoint, look for a specific substring in the returned html"""

    # When
    response = test_client.get(url)

    # Then
    result = response.data.decode()
    assert target in result

def test_load_service():
    """ Check whether expected columns in row """

    assert 'firstName' in load_service()[0]
    assert 'surName' in load_service()[0]
    assert 'nameID' in load_service()[0]