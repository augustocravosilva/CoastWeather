from bs4 import BeautifulSoup
import urllib




def search_page(page):
    html = urllib.urlopen(page).read()
    soup = BeautifulSoup(html)
    data = []
    
    items = soup.findAll('p', {'class': 'title-details'})
    count = 0
    for item in items:
        data.append({})
        data[count]['place'] = item.find('span', {'class': 'municipality'}).get_text()
        innerhtml = urllib.urlopen(item.a.get('href')).read()
        innersoup = BeautifulSoup(innerhtml)
        moreitems = innersoup.findAll('meta')
        for moreitem in moreitems:
            if moreitem.has_key('property'):
                if moreitem['property'] == 'og:title' or moreitem['property'] == 'og:latitude' or moreitem['property'] == 'og:longitude':
                    data[count][moreitem['property'][3:]] = moreitem['content']

        count += 1

    items = soup.findAll('p', {'class': 'figure'})
    count = 0
    for item in items:
        data[count]['picture'] = item.a.img.get('src')
        count += 1

    items = soup.findAll('div', {'class': 'info-detail'})
    count = 0
    for item in items:
        if item.find('li', {'class': 'ico-restaurante'}):
            data[count]['restaurant'] = True
        else:
            data[count]['restaurant'] = False
        if item.find('li', {'class': 'ico-bandeira-azul'}):
            data[count]['blueFlag'] = True
        else:
            data[count]['blueFlag'] = False
        if item.find('li', {'class': 'ico-parque-estacionamento'}):
            data[count]['parking'] = True
        else:
            data[count]['parking'] = False
        if item.find('li', {'class': 'ico-toldos'}):
            data[count]['umbrella'] = True
        else:
            data[count]['umbrella'] = False
        count += 1

    for beach in data:
        if beach.has_key('title'):
            print 'INSERT INTO BEACH(name, latitude, longitude, place, picture, parking, blueFlag, restaurant, umbrella) VALUES ("%s", %s, %s, "%s", "%s", %s, %s, %s, %s);' % (beach['title'], beach['latitude'], beach['longitude'], beach['place'], beach['picture'], beach['parking'], beach['blueFlag'], beach['restaurant'], beach['umbrella'])

    nextp = soup.find('a', {'class': 'linkNext'})
    if nextp:
        search_page(nextp.get('href'))

search_page("http://praias.sapo.pt/praias/norte/")