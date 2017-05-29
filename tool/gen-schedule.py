import random

with open('time.csv', 'w') as f:
    for i in range(0, 80):
        red = random.randint(0, 225)
        green = random.randint(0, 225)
        blue = random.randint(0, 225)

        driveoff = 0
        drivein = 0

        while abs(driveoff - drivein) < 30:
            driveoff = random.randint(0, 24*60 - 1)
            drivein = random.randint(0, 24*60 - 1)

        f.write(str(i) + ',' + str(red) + ',' + str(green) + ',' + str(blue) + ','
                + str(driveoff) + ',' + str(drivein) + '\n')
