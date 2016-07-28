package hu.gabornovak.rssreader.logic;

import hu.gabornovak.rssreader.logic.gateway.RssGateway;
import hu.gabornovak.rssreader.logic.gateway.RssXmlParser;

/**
 * Created by gnovak on 7/28/2016.
 */

public interface GatewayProvider {
    RssGateway getRssGateway();
    RssXmlParser getRssXmlParser();
}
