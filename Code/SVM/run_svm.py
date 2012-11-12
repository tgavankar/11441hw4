import itertools
import re
import subprocess

cs = [.00001, .000025, .00005, .000075, .0001, .00025, .0005, .00075, .001, .0025, .005, .0075, .01]
labels = [i for i in xrange(1,18)]

for c in cs:
    for label in labels:
        with open("data/citeseer.train.ltc.svm") as fin, file("tempin.svm", "w") as ftemp:
            for line in fin:
                split = line.rstrip().split()
                if split[0] != str(label):
                    split[0] = "-1"
                else:
                    split[0] = "1"
                print >> ftemp, ' '.join(split)
        subprocess.call(".\svm_learn.exe -c %f tempin.svm models/%d.txt" % (c, label), shell=True)
        subprocess.call(".\svm_classify.exe data/citeseer.test.ltc.svm models/%d.txt out/%d.txt" % (label, label))
    
    f1 = open("out/1.txt") 
    f2 = open("out/2.txt") 
    f3 = open("out/3.txt") 
    f4 = open("out/4.txt") 
    f5 = open("out/5.txt") 
    f6 = open("out/6.txt") 
    f7 = open("out/7.txt") 
    f8 = open("out/8.txt") 
    f9 = open("out/9.txt") 
    f10 = open("out/10.txt")
    f11 = open("out/11.txt")
    f12 = open("out/12.txt")
    f13 = open("out/13.txt")
    f14 = open("out/14.txt")
    f15 = open("out/15.txt")
    f16 = open("out/16.txt")
    f17 = open("out/17.txt")

    with open("data/citeseer.test.ltc.svm") as fin, file("svm%f.txt" % c, "w") as outputFile:
        for line in itertools.izip(fin, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17):
            max = float('-inf')
            maxLab = -1
            for i in xrange(1, len(line)):
                score = float(line[i])
                if score > max:
                    max = score
                    maxLab = int(i)
            print >> outputFile, "%d %s" % (maxLab, line[0].split()[0])

    f1.close()
    f2.close()
    f3.close()
    f4.close()
    f5.close()
    f6.close()
    f7.close()
    f8.close()
    f9.close()
    f10.close()
    f11.close()
    f12.close()
    f13.close()
    f14.close()
    f15.close()
    f16.close()
    f17.close()