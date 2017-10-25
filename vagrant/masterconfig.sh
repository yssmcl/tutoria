#!/bin/bash

echo 'Configurando puppet master...'
sudo sed -i 's/START=no/START=yes/g' /etc/default/puppetmaster
sudo sed -i 's/\ localhost/\ puppetmaster.example.com/g' /etc/hosts
sudo service puppetmaster restart

sudo sed -i '/\/var\/log\/puppet/a \server=puppetmaster.example.com' /etc/puppet/puppet.conf
echo 'autosign = true' | sudo tee --append /etc/puppet/puppet.conf

echo 'Habilitando puppet agent...'
sudo puppet agent --enable
sudo service puppet restart
