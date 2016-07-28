package hu.gabornovak.rssreader.impl;

import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;
import hu.gabornovak.rssreader.impl.gateway.DefaultRssXmlParser;
import hu.gabornovak.rssreader.logic.GatewayProvider;
import hu.gabornovak.rssreader.logic.gateway.RssGateway;
import hu.gabornovak.rssreader.logic.gateway.RssXmlParser;

/**
 * Singleton class, to provide the default gateways for the interactors
 *
 * Created by gnovak on 7/28/2016.
 */

public enum DefaultGatewayProvider implements GatewayProvider {
    INSTANCE;

    @Override
    public RssGateway getRssGateway() {
        return new DefaultRssGateway(getRssXmlParser());
    }

    @Override
    public RssXmlParser getRssXmlParser() {
        return new DefaultRssXmlParser();
    }
}
