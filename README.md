# qa-load-tests

LoadSimulationSetUp this is where Cluster setup is created.
    Set Cluster to desired cluster to test
        HC15
        SC1
        SC11
Select the load profile to run
Validate that in the profile selected to run that setUp is correct for the load testing needs
 setUp(ChatLoadTest.inject(rampUsers(5) over (1 minute)))