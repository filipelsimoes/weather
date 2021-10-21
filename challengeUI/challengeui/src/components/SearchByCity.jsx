import React from "react";
import { Input } from "antd";

const { Search } = Input;

export default function SearchByCity({
  search,
  handleSearch,
  setSearch,
  cityBySearch,
  errorSearch,
}) {
  return (
    <div>
      <div className="search">
        <Search
          placeholder="Search for a city..."
          onSearch={() => handleSearch(search)}
          value={search}
          onChange={(value) => setSearch(value.target.value)}
          enterButton
        ></Search>
      </div>
      {errorSearch && <div className="error">City doesn't exist</div>}
      <div className="container-results-by-city">
        {cityBySearch !== undefined && (
          <table>
            <tr>
              <th>City</th>
              <th>Weather</th>
              <th>Temperature</th>
              <th>Temperature Min.</th>
              <th>Temperature Max.</th>
              <th>Humidity</th>
              <th>Pressure</th>
            </tr>
            <tr>
              <td>{cityBySearch.name}</td>
              <td>{cityBySearch.description}</td>
              <td>{cityBySearch.humidity}</td>
              <td>{cityBySearch.pressure}</td>
              <td>{cityBySearch.temperature}</td>
              <td>{cityBySearch.temperatureMax}</td>
              <td>{cityBySearch.temperatureMin}</td>
            </tr>
          </table>
        )}
      </div>
    </div>
  );
}
