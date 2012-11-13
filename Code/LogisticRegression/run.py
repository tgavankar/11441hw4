import shutil
import subprocess
import time

cs = [.00001, .000025, .00005, .000075, .0001, .00025, .0005, .00075, .001, .0025, .005, .0075, .01]

for c in cs:
    data = 'train=data/train.txt\ntest=data/test.txt\nc=%f' % c
    with file("data.txt", "w") as fdata:
        print >> fdata, data
    print "Running %f" % c
    subprocess.call("java -jar main.jar > out/%f.txt" % c, shell=True)

'''
with file("times.txt", "a") as ftimes:
    for c in cs:
        with open("out/%f.txt" % c) as fdata, file("temp.txt", "w") as ftemp:
            print >> ftimes, fdata.readline().strip()
            for line in fdata:
                ftemp.write(line)
            shutil.copyfile("temp.txt", "out/%f.txt" % c)
            time.sleep(0.5)
'''