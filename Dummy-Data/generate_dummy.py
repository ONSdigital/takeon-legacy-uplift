import csv
import random
from faker import Faker


REFERENCE = 69000000000  # Start reference number (ruref)
WOW_START_REF = 2900000000
WOW_END_REF = 2999999999


"""
Creating univext File
"""


def create_univext_file(source_file, output_file):
    with open(source_file, "r") as file_in:
        reader = csv.reader(file_in, delimiter=':')
        with open(output_file, 'w+') as file_new:
            reference = REFERENCE
            for i in reader:
                reference = reference + 1
                string_var = str(reference) + ':' + ':'.join(i) + "\n"
                string_var.replace("\"", "")
                file_new.write(string_var)


"""
Creating finalsel File
"""


def create_finalsel_file(source_file, output_file):
    fake = Faker('en_GB')
    with open(source_file, "r") as file_in:
        reader = csv.reader(file_in, delimiter=':')
        with open(output_file, 'w') as file_new:
            writer = csv.writer(file_new, delimiter=':', lineterminator='\n')
            for i in reader:
                if 'N' not in i[20]:
                    reporting_unit_reference = i[0]
                    live_local_unit = i[14]
                    live_vat = i[15]
                    live_paye = i[16]
                    legal_status = i[17]
                    region = i[18]
                    cell_number = i[19]
                    selection_marker = i[20]
                    inclusion_exclusion_marker = i[22]

                    i[1] = fake.random_uppercase_letter()  # Check letter

                    i[14] = reporting_unit_reference[1:]  # enterprise reference
                    i[15] = random.randrange(WOW_START_REF, WOW_END_REF)  # wow enterprise reference
                    i[16] = fake.random_number(12)  # VAT reference
                    i[17] = fake.random_number(13)  # VAT paye
                    i[18] = fake.random_number(8)  # Company registration number
                    i[19] = live_local_unit
                    i[20] = live_vat
                    i[21] = live_paye

                    del i[22]  # Deleting empty column

                    i.extend((legal_status, "E", region, fake.date(pattern='%d/%m/%Y'),
                              fake.company(), fake.company_suffix(), "", fake.company(),
                              "", "", fake.building_number(), fake.street_name(), fake.city(),
                              fake.country(), "", fake.postcode(),
                              random.choice(['Franchise', 'Sole Trader', '']), "", "",
                              fake.name(), fake.phone_number(), fake.phone_number(),
                              selection_marker, inclusion_exclusion_marker, cell_number,
                              "0004", "*", random.choice(['E', 'S'])))
                    writer.writerow(i)


if __name__ == "__main__":
    create_univext_file("univext019_202003.csv", "univext.csv")
    create_finalsel_file("univext.csv", "finalsel.csv")
