#!/bin/sh

echo 'Configurando puppet agent...'
echo '192.168.200.10 puppetmaster.example.com puppetmaster' | sudo tee --append /etc/hosts
sudo sed -i '/\/var\/log\/puppet/a \server=puppetmaster.example.com' /etc/puppet/puppet.conf
sudo sed -i 's/START=no/START=yes/g' /etc/default/puppet

echo 'Reiniciando puppet agent em modo de teste...'
sudo service puppet restart
sudo puppet agent --enable
sudo puppet agent --test

echo '10.0.2.2    127.0.0.1' | sudo tee --append /etc/hosts
echo '10.0.2.2    localhost' | sudo tee --append /etc/hosts
