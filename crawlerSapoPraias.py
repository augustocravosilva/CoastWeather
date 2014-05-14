from bs4 import BeautifulSoup
import urllib




def search_page(page):
    html = urllib.urlopen(page).read()
    soup = BeautifulSoup(html)
    items = soup.findAll('p', {'class': 'title-details'})
    for item in items:
        innerhtml = urllib.urlopen(item.a.get('href')).read()
        innersoup = BeautifulSoup(innerhtml)
        moreitems = innersoup.findAll('meta')
        data = {}
        for moreitem in moreitems:
            if moreitem.has_key('property'):
                if moreitem['property'] == 'og:locality':
                    print 'INSERT INTO PRAIAS(Nome, Latitude, Longitude) VALUES ("%s", %s, %s);' % (data['title'], data['latitude'], data['longitude'])
                if moreitem['property'] == 'og:title' or moreitem['property'] == 'og:latitude' or moreitem['property'] == 'og:longitude':
                    data[moreitem['property'][3:]] = moreitem['content']

    nextp = soup.find('a', {'class': 'linkNext'})
    if nextp:
        search_page(nextp.get('href'))
    else:
        print 'heh'

search_page("http://praias.sapo.pt/praias/centro/")
search_page("http://praias.sapo.pt/praias/lisboa/")
search_page("http://praias.sapo.pt/praias/alentejo/")
search_page("http://praias.sapo.pt/praias/algarve/")

