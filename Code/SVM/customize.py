paths = ["train", "test"]

def get45(s):
	scores = [get(s, 20), get(s, 24), get(s, 21), get(s, 25), get(s, 22), get(s, 26)]
	return sum(scores) / len(scores)

def get46(s):
	scores = [get(s, 8), get(s, 14), get(s, 6), get(s, 7)]
	return reduce(lambda x,y: x*y, [1000000 * x + 1 for x in scores], 1) / 1000000;

def get47(s):
	scores = [get(s, 1), get(s, 32)]
	return reduce(lambda x,y: x*y, scores, 1);

def get(split, f):
	return float(split[f + 1].split(":")[1])

for path in paths:
	with open("%s.txt" % path) as fin, file("custom_%s.txt" % path, "w") as fout:
		for line in fin:
			hashind = line.find("#")
			if hashind > -1:
				line = line[0:hashind]
			line = line.strip()
			split = line.split(" ")
			

			# 45 = AVG(F20 F24 F21 F25 F22 F26)
			# BM25 / LMIR title combination
			field45 = get45(split)
			


			# 46 = (F8 * 1000000 + 1) * (F14 * 1000000 + 1) * (F6 * 1000000 + 1) * (F7 * 1000000 + 1) / 1000000
			# Combine rankings
			field46 = get46(split)
			


			# 47 = F1 * F32
			# Combine BM25 and TFIDF
			field47 = get47(split)



			line += " 45:%f 46:%f 47:%f\n" % (field45, field46, field47)
			fout.write(line)
