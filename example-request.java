new SpigetClient().request("/resources").sorted().size(20).getSorted(Resource.class, (response, error) -> {
    for(Resource resource : response) {
        System.out.println(resource.getName());
    }
});
