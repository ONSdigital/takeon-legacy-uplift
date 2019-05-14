import csv
import random
from faker import Faker


fake = Faker('en_GB')

with open("univext019_202003.csv", "r") as file_in:
    reader = csv.reader(file_in, delimiter=':')
    with open('univext.csv', 'w+') as file_new:
        for i in reader:
            ruref = str(random.randrange(69000000000, 69999999999))
            string_var = ruref + ':' + ':'.join(i) + "\n"
            string_var.replace("\"", "")
            file_new.write(string_var)

with open("univext.csv", "r") as file_in:
    reader = csv.reader(file_in, delimiter=':')
    with open('finalsel.csv', 'w') as file_new:
        writer = csv.writer(file_new, delimiter=':', lineterminator='\n')
        for i in reader:
            if 'N' not in i[20]:
                entref = i[0]
                live_lu = i[14]
                live_vat = i[15]
                live_paye = i[16]
                legalstatus = i[17]
                region = i[18]
                cell_no = i[19]
                selmkr = i[20]
                inclexcl = i[22]

                i[1] = fake.random_uppercase_letter()
                i[14] = entref[1:]
                i[15] = random.randrange(2900000000, 2999999999)
                i[16] = fake.random_number(12)
                i[17] = fake.random_number(13)
                i[18] = fake.random_number(8)
                i[19] = live_lu
                i[20] = live_vat
                i[21] = live_paye

                del i[22]

                i.extend((legalstatus, "E", region, fake.date(pattern='%d/%m/%Y'),
                          fake.company(), fake.company_suffix(), "", fake.company(), "", "", fake.building_number(),
                          fake.street_name(), fake.city(), fake.country(), "", fake.postcode(),
                          random.choice(['Franchise', 'Sole Trader', '']), "", "", fake.name(), fake.phone_number(),
                          fake.phone_number(), selmkr, inclexcl, cell_no, "0004", "*", random.choice(['E', 'S'])))
                writer.writerow(i)

