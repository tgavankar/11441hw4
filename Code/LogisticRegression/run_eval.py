import glob
import subprocess

for path in glob.glob("./out/*.txt"):
  	print path    
    #subprocess.call("perl eval.pl test.txt %s %s-score 0" % (path, path), shell=True)
