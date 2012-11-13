import subprocess

cs = [.00001, .000025, .00005, .000075, .0001, .00025, .0005, .00075, .001, .0025, .005, .0075, .01]

for c in cs:
    #print "Learning %f" % c
    #subprocess.call(".\svm_learn.exe -c %f -z p train.txt models/%f.txt > text/learn/%f.txt" % (c, c, c), shell=True)
    print "Classifying %f" % c
    subprocess.call(".\svm_classify.exe test.txt models/%f.txt out/%f.txt > text/class/%f.txt" % (c, c, c), shell=True)
    