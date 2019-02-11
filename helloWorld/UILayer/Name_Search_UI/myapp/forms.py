
from flask_wtf import FlaskForm
from wtforms import StringField, IntegerField, validators
from wtforms.validators import InputRequired, NumberRange


class SearchForm(FlaskForm):

    first_name = StringField('Search By First Name',
                       [validators.DataRequired(), validators.Length(min=0, max=50)])