
from collections import OrderedDict
from csv import DictWriter
import os

from mimesis import Person

COUNT = 100
LOCAL = 'en-gb'
HERE = os.path.dirname(__file__)

person = Person(LOCAL)

records = []
for _ in range(COUNT):
    record = OrderedDict(
        name=person.full_name(),
        email=person.email(),
        age=person.age(minimum=18, maximum=45),
        height=person.height(),
        blood_type=person.blood_type()
    )
    records.append(record)


with open(os.path.join(HERE, './fakedata.csv'), 'w') as f:
    writer = DictWriter(f, fieldnames=records[0].keys())
    writer.writeheader()
    writer.writerows(records)
