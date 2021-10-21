import React from "react";
import { SearchOutlined, DeleteOutlined } from "@ant-design/icons";
import { Input, Tooltip, Button } from "antd";
import { TablePagination } from "react-pagination-table";

export default function SearchByCoords({
  errorCords,
  citiesBySearchCoords,
  handleSearchByCoords,
  latitude,
  longitude,
  cnt,
  handleClear,
  setLatitude,
  setLongitude,
  setCnt,
}) {
  const Header = [
    "City",
    "Weather",
    "Temperature",
    "Temperature Min",
    "Temperature Max",
    "Humidity",
    "Pressure",
  ];

  return (
    <div>
      <div className="inputs">
        <div className="inputs-latitude">
          <Tooltip
            trigger={["focus"]}
            title={"Write a latitude"}
            placement="topLeft"
            overlayClassName="numeric-input"
          >
            <Input
              onChange={(value) => setLatitude(value.target.value)}
              placeholder="Write a latitude"
              value={latitude}
              maxLength={25}
              addonBefore="Latitude"
            />
          </Tooltip>
        </div>
        <div className="inputs-longitude">
          <Tooltip
            trigger={["focus"]}
            title={"Insert a longitude"}
            placement="topLeft"
            overlayClassName="numeric-input"
          >
            <Input
              onChange={(value) => setLongitude(value.target.value)}
              placeholder="Write a longitude"
              maxLength={25}
              value={longitude}
              addonBefore="Longitude"
            />
          </Tooltip>
        </div>
        <div className="inputs-cnt">
          <Tooltip
            trigger={["focus"]}
            title={"Number of cities to display"}
            placement="topLeft"
            overlayClassName="numeric-input"
          >
            <Input
              onChange={(value) => setCnt(value.target.value)}
              placeholder="Number of cities"
              maxLength={25}
              value={cnt}
              type="number"
              min={1}
              addonBefore="Number of cities"
            />
          </Tooltip>
        </div>
        <div className="searchByCoords-buttons">
          <Button
            type="primary"
            icon={<SearchOutlined />}
            onClick={() => handleSearchByCoords(latitude, longitude, cnt)}
          >
            Search
          </Button>
        </div>
        <Button
          type="danger"
          icon={<DeleteOutlined />}
          onClick={() => handleClear()}
        >
          Clear
        </Button>
      </div>
      {errorCords && <div className="error">Wrong latitude or longitude</div>}
      {citiesBySearchCoords != undefined && (
        <div className="container-results-by-city">
          <TablePagination
            headers={Header}
            data={citiesBySearchCoords}
            columns="name.description.temperature.temperatureMin.temperatureMax.humidity.pressure"
            perPageItemCount={10}
            totalCount={citiesBySearchCoords.length}
            arrayOption={[["size", "all", " "]]}
            paginationClassName="pagination"
          />
        </div>
      )}
    </div>
  );
}
