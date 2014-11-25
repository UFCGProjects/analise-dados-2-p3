from copy import deepcopy


def main():
    dic = {}
    terms = {}

    csvInput = open("csv/data.txt", "r")

    # i = 0
    for line in csvInput.read().split("\n"):
        values = line.split("#")

        if (len(values) > 1):
            append(dic, terms, values[0], values[1])

        # i += 1

        # if i > 40000:
            # break

    csvInput.close()

    compute(dic, sorted(terms.keys()))


def append(dic, terms, artist, term):
    if artist not in dic:
        dic[artist] = [term]
    else:
        dic[artist].append(term)

    if term not in terms:
        terms[term] = 0


def compute(dic, terms):
    result = {}

    for artist in dic:
        for i in range(len(dic[artist])):
            for j in range(i + 1, len(dic[artist])):
                put(result, dic[artist][i], dic[artist][j])

    filterMin(terms, result)
    makeJSON(terms, result)


def filterMin(terms, result):
    tempResult = deepcopy(result)
    tempTerms = deepcopy(terms)

    min_links = 200

    for termA in tempResult:
        for termB in tempResult[termA]:

            if result[termA][termB] < min_links:
                result[termA].pop(termB, None)

        if len(result[termA]) == 0:
            result.pop(termA)


    for term in tempTerms:
        hasLink = False

        if term in result:
            hasLink = True
        else:
            for termA in result:
                if term in result[termA]:
                    hasLink = True

        if not hasLink:
            terms.pop(getIndex(terms, term))


def makeJSON(nodes, links):
    json = "var graph = { \"nodes\": ["

    json += ",".join(["{\"name\": \"%s\", \"link_count\": %d}" % (node, link_count(nodes, links, node)) for node in nodes])

    json += "],\"links\": ["

    json += ",".join(["{\"source\": %d,\"target\": %d,\"value\": %d}" % (getIndex(nodes, termA), getIndex(nodes, termB), links[termA][termB]) for termA in links for termB in links[termA]])

    json += "]};"

    writeFile(json)


def link_count(nodes, links, node):
    result = 0

    if node in links:
        result += len(links[node])

    for term in links:
        if node in links[term]:
            result += 1

    return result


def writeFile(json):
    csvOutput = open("graph.json", "w")

    csvOutput.write(json)

    csvOutput.close()



def put(result, termA, termB):
    if termB in result and termA in result[termB]:
        result[termB][termA] += 1
    else:
        if termA not in result:
            result[termA] = {}

        if termB not in result[termA]:
            result[termA][termB] = 0

        result[termA][termB] += 1


def getIndex(terms, value):
    for i in range(len(terms)):
        if terms[i] == value:
            return i
    return -1

if __name__ == "__main__":
    main()
